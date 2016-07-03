# Paper
Minecraft Mod Loader

## Our goal
Our goal with Paper is to create a modern Minecraft mod loader, utilizing the latest Java standards, in a lightweight fashion.

## Code Style
- 4 spaces should be used in place of tabs.
- Braces must be placed on the same line, for example:
```java
public void doSomething() {
    if (this.condition) {
        this.doAnotherThing();
    } else {
        this.doSomethingElse();
    }
}
```
- 'this.' should be used wherever possible.
    - Bad: `greeting = "Hello World"`
    - Good: `this.greeting = "Hello World"`
- If a singleton is made, it should be made using an enum.
    - Bad:
    ```java
    public class Singleton {
        public static Singleton INSTANCE;
    }
    ```
    - Good:
    ```java
    public enum Singleton {
        INSTANCE;
    }
    ```
- All static field names should be uppercased and use underscores. For example, `SOME_VALUE` would be used instead of `someValue`
- Static fields should be referenced with their parent class name, even when inside that class.
    - Bad:
    ```java
    public class Something {
        public static String STRING = "A String!";
        
        public void doSomething() {
            System.out.println(STRING);
        }
    }
    ```
    - Good:
    ```java
    public class Something {
        public static String STRING = "A String!";
        
        public void doSomething() {
            System.out.println(Something.STRING);
        }
    }
    ```
- JavaDocs should be made for everything, with a few exceptions.
    - A description for a getter does not need to be made (although can be), as the @return should already describe what the method does.
    - A description for a constructor does not need to be made (although can be), as the class JavaDoc should describe the class.

## Contributing
1. Fork Paper
2. Create your feature branch: `git checkout -b my-new-feature`
3. Setup paper: `gradlew.bat setupPaper setupIDEA --refresh-dependencies`
3. Commit your changes: `git commit -m "Add some feature"`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request
