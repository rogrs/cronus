'use strict';

angular.module('jhipsterApp')
    .controller('PlanoDetailController', function ($scope, $rootScope, $stateParams, entity, Plano, Projeto) {
        $scope.plano = entity;
        $scope.load = function (id) {
            Plano.get({id: id}, function(result) {
                $scope.plano = result;
            });
        };
        $rootScope.$on('jhipsterApp:planoUpdate', function(event, result) {
            $scope.plano = result;
        });
    });
