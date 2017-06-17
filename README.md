# MBRS17
Methodologies for Rapid Software Development Course Project

## Running the app
//TODO

## Meta-model used for modeling
![Meta-model](https://github.com/vladimirivkovic/MBRS17/blob/master/Meta-model.png "Meta-model")

## Model used for code generation
![Model](https://github.com/vladimirivkovic/MBRS17/blob/master/Model.png "Model")

## Generated app example
### Back-end
The example of a generated back-end layer of an application can be seen on the following [git repository](https://github.com/TodorovicNikola/MBRS17APP).
The running example of the back-end app is running on an Azure cloud server an is linked directly to the aforementioned git repository.
  
### Front-end
  //TODO


## Techniques used for preserving manually added code
//TODO
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
