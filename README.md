# MBRS17
Methodologies for Rapid Software Development Course Project

## Meta-model used for modeling
![Meta-model](https://github.com/vladimirivkovic/MBRS17/blob/master/Meta-model.svg "Meta-model")


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
