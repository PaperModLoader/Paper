package xyz.papermodloader.paper;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.launchwrapper.Launch;
import xyz.papermodloader.paper.launcher.side.Side;
import xyz.papermodloader.paper.util.Arguments;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.Locale;

/**
 * Main class for Paper development.
 */
public class PaperStart {
    /**
     * Main method for Paper development.
     *
     * @param args the program args. Use {@code --side [side]} to run Minecraft, or use {@code --hooks true} to regenerate the hooks.
     */
    public static void main(String[] args) throws IOException {
        Arguments arguments = new Arguments(args);

        if (!arguments.has("side")) {
            throw new RuntimeException("Missing side argument!");
        }

        Side side = Side.valueOf(arguments.get("side").toUpperCase(Locale.ENGLISH));
        boolean dev = arguments.has("dev") && arguments.get("dev").equals("true");

        arguments.add("version", "1.10.2");
        arguments.add("tweakClass", String.format("xyz.papermodloader.paper.launcher.tweaker.%s.Paper%s%sTweaker", side == Side.CLIENT ? "client" : "server", side == Side.CLIENT ? "Client" : "Server", dev ? "Dev" : ""));

        if (side == Side.CLIENT) {
            if (dev) {
                System.setProperty("org.lwjgl.librarypath", new File(System.getProperty("user.home"), ".gradle" + File.separator + "caches" + File.separator + "paper" + File.separator + "natives").getAbsolutePath());

                if (arguments.has("password")) {
                    YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1")).createUserAuthentication(Agent.MINECRAFT);
                    auth.setUsername(arguments.get("username"));
                    auth.setPassword(arguments.get("password"));
                    arguments.remove("password");

                    try {
                        auth.logIn();
                    } catch (AuthenticationException e) {
                        e.printStackTrace();
                        return;
                    }

                    arguments.add("accessToken", auth.getAuthenticatedToken());
                    arguments.add("uuid", auth.getSelectedProfile().getId().toString().replace("-", ""));
                    arguments.add("username", auth.getSelectedProfile().getName());
                    arguments.add("userType", auth.getUserType().getName());
                    arguments.add("userProperties", new GsonBuilder().registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create().toJson(auth.getUserProperties()));
                } else {
                    arguments.add("accessToken", "PaperModLoader");
                }

                arguments.add("assetsDir", new File(System.getProperty("user.home"), ".gradle" + File.separator + "caches" + File.separator + "paper" + File.separator + "assets").getAbsolutePath());
                arguments.add("assetIndex", "1.10");
            }
        }

        arguments.remove("side");
        arguments.remove("dev");

        System.out.println("=======================================");
        System.out.println("Paper " + Paper.VERSION);
        System.out.println("https://github.com/PaperModLoader/Paper");
        System.out.println("=======================================");

        Launch.main(arguments.build());
    }
}
