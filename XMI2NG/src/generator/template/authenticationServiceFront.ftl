(function () {
    angular
        .module('app.AuthenticationModule', ['ngStorage','ngRoute'])
        .factory('AuthenticationService', Service);

    function Service($http,$rootScope,$localStorage,$window) {
        var service = {};

        service.login = login;
        service.logout = logout;
        service.getCurrentUser = getCurrentUser;

        return service;

        function login(username, password, callback) {
          var host=$rootScope.host;
            //$http.post('/../../oauth/token', { name: username, password: password })
            $http({
                method: 'POST',
                url: host+'api/Login',
                data: {username: username, password: password }
            })
            .success(function (response) {
                // ukoliko postoji token, prijava je uspecna
                if (response) {
                    // korisnicko ime, token i rola (ako postoji) cuvaju se u lokalnom skladištu
                    var currentUser = { username: username, token: response }

                    console.log(currentUser);
                    $rootScope.currentUser = currentUser;
                    $localStorage.currentUser=currentUser;

                    // jwt token dodajemo u to auth header za sve $http zahteve
                    $http.defaults.headers.common.Authorization = response;
                    // callback za uspesan login
                    callback(true);
                  //  $state.go('dashboard');
                } else {
                    // callback za neuspesan login
                    callback(false);
                }
            })
            .error(function (respnse) {
                callback(false);
            });
        }

        function logout() {
            delete $localStorage.currentUser;
            $http.defaults.headers.common.Authorization = '';

            $window.location.href='#/login'
        }

        function getCurrentUser() {
            return $localStorage.currentUser;
        }
      }
})();
