  var app = angular.module("MyApp", []);

        app.controller("PostsCtrl", function($scope, $http) {
          $http.get('rs/v1/planos').
            success(function(data, status, headers, config) {
              $scope.posts = data;
            }).
            error(function(data, status, headers, config) {
            	alert( "Falha: " + JSON.stringify({data: data}));
            });
        });
        
        
        var app2 = angular.module("MyApp2", []);

        app2.controller("MyController", function($scope, $http) {
          $http.get('rs/v1/scripts').
            success(function(data, status, headers, config) {
              $scope.Fruits = data;
            }).
            error(function(data, status, headers, config) {
            	alert( "Falha: " + JSON.stringify({data: data}));
            });
        });

 
        