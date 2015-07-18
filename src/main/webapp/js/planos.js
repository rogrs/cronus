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
        
        app.controller("ScriptsCtrl", function($scope, $http) {
            $http.get('rs/v1/scripts').
              success(function(data, status, headers, config) {
                $scope.posts2 = data;
              }).
              error(function(data, status, headers, config) {
              	alert( "Falha: " + JSON.stringify({data: data}));
              });
          });

        