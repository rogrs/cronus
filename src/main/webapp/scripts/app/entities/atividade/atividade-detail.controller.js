'use strict';

angular.module('jhipsterApp')
    .controller('AtividadeDetailController', function ($scope, $rootScope, $stateParams, entity, Atividade, Plano, Script) {
        $scope.atividade = entity;
        $scope.load = function (id) {
            Atividade.get({id: id}, function(result) {
                $scope.atividade = result;
            });
        };
        $rootScope.$on('jhipsterApp:atividadeUpdate', function(event, result) {
            $scope.atividade = result;
        });
    });
