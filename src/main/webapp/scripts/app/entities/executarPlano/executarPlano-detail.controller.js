'use strict';

angular.module('jhipsterApp')
    .controller('ExecutarPlanoDetailController', function ($scope, $rootScope, $stateParams, entity, ExecutarPlano, Atividade) {
        $scope.executarPlano = entity;
        $scope.load = function (id) {
            ExecutarPlano.get({id: id}, function(result) {
                $scope.executarPlano = result;
            });
        };
        $rootScope.$on('jhipsterApp:executarPlanoUpdate', function(event, result) {
            $scope.executarPlano = result;
        });
    });
