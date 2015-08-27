'use strict';

angular.module('jhipsterApp')
    .controller('ProjetoDetailController', function ($scope, $rootScope, $stateParams, entity, Projeto) {
        $scope.projeto = entity;
        $scope.load = function (id) {
            Projeto.get({id: id}, function(result) {
                $scope.projeto = result;
            });
        };
        $rootScope.$on('jhipsterApp:projetoUpdate', function(event, result) {
            $scope.projeto = result;
        });
    });
