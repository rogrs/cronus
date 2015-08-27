'use strict';

angular.module('jhipsterApp')
    .controller('ScriptDetailController', function ($scope, $rootScope, $stateParams, entity, Script, Plugin) {
        $scope.script = entity;
        $scope.load = function (id) {
            Script.get({id: id}, function(result) {
                $scope.script = result;
            });
        };
        $rootScope.$on('jhipsterApp:scriptUpdate', function(event, result) {
            $scope.script = result;
        });
    });
