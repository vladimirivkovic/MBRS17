# MBRS17
Methodologies for Rapid Software Development Course Project
![Meta-model](https://github.com/vladimirivkovic/MBRS17/blob/master/images/image.png "logo")

## Example application
An example of a generated application can be found [here](http://mbrs17.surge.sh). </br>
</br>
#### Login credentials: 
* username: admin 
* password: admin 

## Prerequisites
This project uses the following technologies and tools:
* [Eclipse IDE](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr)
* [FreeMarker](http://freemarker.org)
* [MagicDraw](https://www.nomagic.com/products/magicdraw)

## Running the app
* Import **XMI2NG** project into your Eclipse workspace
* Run the **src/generator/Main.java** class with the following command line arguments:
    * path to model file in *XMI* format exported from *MagicDraw*
    * path to directory where the code generated from given model will be saved

## Meta-model used for modeling
![Meta-model](https://github.com/vladimirivkovic/MBRS17/blob/master/images/Meta-model.png "Meta-model")

## Model used for code generation
![Model](https://github.com/vladimirivkovic/MBRS17/blob/master/images/Model.png "Model")

## Generated app example
### Back-end
Back-end layer is implemented by using [Microsoft ASP .NET WEB API](https://www.asp.net/web-api) with the use of additional Microsoft's technologies such as [Entity Framework Code First](https://msdn.microsoft.com/en-us/library/jj193542(v=vs.113).aspx) and [OData .NET](https://www.asp.net/web-api/overview/odata-support-in-aspnet-web-api).  An example of a generated back-end layer of an application can be seen on the following [git repository](https://github.com/TodorovicNikola/MBRS17APP).
An instance of the back-end app is running on an Azure cloud server and is linked directly to the aforementioned git repository.
  
### Front-end
Front-end layer is implemented by using [AngularJS 1.5](https://angularjs.org/) and [Bootstrap](http://getbootstrap.com/) frameworks.
An example of a generated front-end layer of an application can be seen [here](https://github.com/vuletic/MBRS17-frontend).


## Techniques used for preserving manually added code
Several techniques are used to provide preserving manually writted code:
* *Protected areas* (code blocks between predefined comments)
* [Partial classes](https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/classes-and-structs/partial-classes-and-methods)    
### Example of usage

#### Custom code for report
```
$scope.Generisi_izvestajClick = function() {
  // USER CODE STARTS HERE
  var initInjector = angular.injector(['ng']);
  var $http = initInjector.get('$http');
  var host=$scope.$parent.host;
  $http.get(host+'api/operations/Generisi_izvestaj?jezik=srp', {responseType: 'arraybuffer'})
   .success(function (data) {
       var file = new Blob([data], {type: 'application/pdf'});
       var fileURL = URL.createObjectURL(file);
       window.open(fileURL);
    });

  // USER CODE ENDS HERE
}
```
#### Partial classes example
An example of the usage of partial classes can be seen [here](https://github.com/TodorovicNikola/MBRS17APP/blob/master/WebApplication1/WebApplication1/Models/Faktura_manual.cs) for model classes and [here](https://github.com/TodorovicNikola/MBRS17APP/blob/master/WebApplication1/WebApplication1/Controllers/Operation_Manual_Controller.cs) for controller classes.


License
----

MIT
