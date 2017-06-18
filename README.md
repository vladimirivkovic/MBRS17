# MBRS17
Methodologies for Rapid Software Development Course Project

## Prerequisites
This project uses the following technologies and tools:
* [Eclipse IDE](http://www.eclipse.org/downloads/packages/eclipse-ide-java-developers/marsr)
* [FreeMarker](http://freemarker.org)
* [MagicDraw](https://www.nomagic.com/products/magicdraw)

## Runnin the app
* Import **XMI2NG** project into your Eclipse workspace
* Run the **src/generator/Main.java** class with the following command line arguments:
    * path to model file in *XMI* format exported from *MagicDraw*
    * path to directory where the code generated from given model will be saved

## Meta-model used for modeling
![Meta-model](https://github.com/vladimirivkovic/MBRS17/blob/master/Meta-model.png "Meta-model")

## Model used for code generation
![Model](https://github.com/vladimirivkovic/MBRS17/blob/master/Model.png "Model")

## Generated app example
### Back-end
An example of a generated back-end layer of an application can be seen on the following [git repository](https://github.com/TodorovicNikola/MBRS17APP).
The running instance of the back-end app is running on an Azure cloud server an is linked directly to the aforementioned git repository.
  
### Front-end
Front-end layer is implemented by using [AngularJS 1.5](https://angularjs.org/) and [Bootstrap](http://getbootstrap.com/) frameworks.
An example of a generated front-end layer of an application can be seen [here](http://mbrs17.surge.sh).


## Techniques used for preserving manually added code
Several techniques are used to provide preserving manually writted code:
* *Protected areas* (code blocks between predefined comments)
* [Partial classes](https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/classes-and-structs/partial-classes-and-methods)    
### Example of usage
//TODO


Custom code for report
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

License
----

MIT
