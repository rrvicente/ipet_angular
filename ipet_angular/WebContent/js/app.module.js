(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run)
    	.factory("MyService", function() {
      	  return {
      	    data: {}
      	  };
      	});

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'view/home/home.view.html',
                controllerAs: 'vm'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'view/login/login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'registerController',
                templateUrl: 'view/register/register.view.html',
                controllerAs: 'vm'
            })

             .when('/cliente', {
                controller: 'crudClienteController',
                templateUrl: 'view/crud/crudCliente.html',
                controllerAs: 'crudClienteController'
            })
            
            .when('/noticias', {
                controller: 'crudNoticiaController',
                templateUrl: 'view/crud/crudNoticia.html',
                controllerAs: 'crudNoticiaController'
            })
            
            .when('/animal', {
                controller: 'crudAnimalController',
                templateUrl: 'view/crud/crudAnimal.html',
                controllerAs: 'crudAnimalController'
            })
            
             .when('/produto', {
                controller: 'crudProdutoController',
                templateUrl: 'view/crud/crudProduto.html',
                controllerAs: 'crudProdutoController'
            })
            
             .when('/atendimento', {
                controller: 'crudAtendimentoController',
                templateUrl: 'view/crud/crudAtendimento.html',
                controllerAs: 'crudAtendimentoController'
            })
            
            .when('/listar.cliente', {
                controller: 'crudClienteController',
                templateUrl: 'view/crud/listarCliente.html',
                controllerAs: 'crudClienteController'
            })

            .when('/listar.atendimento', {
                controller: 'crudClienteController',
                templateUrl: 'view/crud/listarAtendimento.html',
                controllerAs: 'crudClienteController'
            })

            .when('/listar.atendimento.itens', {
                controller: 'listarAtendimentoItensController',
                templateUrl: 'view/crud/listarAtendimentoItens.html',
                controllerAs: 'listarAtendimentoItensController'
            })
            
            .otherwise({ redirectTo: 'view/home/home.view.html' });
    }
    
    run.$inject = ['$rootScope', '$location', '$cookieStore', '$http'];
    function run($rootScope, $location, $cookieStore, $http) {
    	
        // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
        }

        $rootScope.showLoginTemplate = true;
        $rootScope.showMenuTemplate = false;
        
        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
         
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            } else {
            	$rootScope.showLoginTemplate = false;
                $rootScope.showMenuTemplate = true;
            }
        });
    }
    
//    angular.service('dataStorage', ['$http', function($http) {
//        var service = this;
//
//        function extract(result) {
//        	return result.data;
//        }
//        
//        service.get = function(key, URL_DO_SEU_BACKEND) {
//           return $http.get(URL_DO_SEU_BACKEND + "usuarios/" + key).then(extract);
//
//        };
//
//      }])
    
})();