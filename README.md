# SimpleHttpServer
this is a simple http server,which supports session manager,media(video,audio)transmission,static file,html render,..etc.
##how to run/use
package _me.gensh.core_ is all about http,it's not necessary to modify them,but you can modify those files below if you want:
* me.gensh.Main.java (program start)
* me.gensh.core.Config.java (Configure file)
* me.gensh.controllers (your logic code)
* me.gensh.router.Router.java (router file)

there is a dome code here already, you can just run id.

##start your logic code
create file "Dome.java" in controllers folder.
add those code to Dome.java:
```java
public static ResponseInterface domeAction = context -> {
        context.render("Hello world");
    };
```
then add the code below to file me.gensh.router.Router.java,in construction method:
```java
public Router(){
  //add your router here
  Add("/dome", Dome.domeAction); //just add this line.
 }
```
then run your code, then type "localhost:8888/dome" in your browser,you can see words "Hello world".
##static file
you can change static folder in Configure file(me.gensh.core.Config.java).
for example:
```java
    public final static String StaticFilePrefix = "/static";
```
then the files in "static" folder can be detected and return to browser if your url is start with "static".
##template render
we use the Freemarker template engine as default render engine.
see [Apache FreeMarker](http://freemarker.org/)
(you can also add your own render engine.)
change your code in section "start your logic code":
```java
   public static ResponseInterface domeAction = context -> {
        String name = context.getParams().getString("name");
        Map<String,String> data = new HashMap<>();
        if (name.isEmpty()) {
             name = "World";
        }
        data.put("title", "Main  page");
        data.put("name", "Worlds");
        context.render("test.ftl", data);
    };
```
then you can add file named 'dome.ftl' in views folder.
*why views folder?*
*you can change template folder by modify configure file:*
```java 
    final static public class View {
        public final static String VIEW = "/your_template_floder/"; //"views" as default.
    }
```