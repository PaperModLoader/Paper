package xyz.papermodloader.paper.launcher;

import com.google.gson.GsonBuilder;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.launchwrapper.Launch;

import java.io.File;
import java.net.Proxy;

public class PaperClient {
    public static void main(String[] args) {
        LaunchArguments arguments = new LaunchArguments(args);

        if (arguments.containsArgument("password")) {
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) (new YggdrasilAuthenticationService(Proxy.NO_PROXY, "1")).createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(arguments.getArgument("username"));
            auth.setPassword(arguments.getArgument("password"));
            arguments.removeArgument("password");

            try {
                auth.logIn();
            } catch (AuthenticationException e) {
                e.printStackTrace();
                return;
            }

            arguments.addArgument("accessToken", auth.getAuthenticatedToken());
            arguments.addArgument("uuid", auth.getSelectedProfile().getId().toString().replace("-", ""));
            arguments.addArgument("username", auth.getSelectedProfile().getName());
            arguments.addArgument("userType", auth.getUserType().getName());
            arguments.addArgument("userProperties", new GsonBuilder().registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create().toJson(auth.getUserProperties()));
        }

        arguments.addArgument("version", "1.10.2");
        arguments.addArgument("assetIndex", "1.10");
        arguments.addArgument("assetsDir", new File(System.getProperty("user.home"), ".gradle" + File.separator + "caches" + File.separator + "paper" + File.separator + "assets").getAbsolutePath());
        arguments.addArgument("accessToken", "PaperModLoader");
        arguments.addArgument("tweakClass", "xyz.papermodloader.paper.launcher.PaperClientTweaker");

        Launch.main(arguments.getArguments());
    }
}
