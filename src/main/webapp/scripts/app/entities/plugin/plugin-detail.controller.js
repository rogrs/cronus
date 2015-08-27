'use strict';

angular.module('jhipsterApp')
    .controller('PluginDetailController', function ($scope, $rootScope, $stateParams, entity, Plugin) {
        $scope.plugin = entity;
        $scope.load = function (id) {
            Plugin.get({id: id}, function(result) {
                $scope.plugin = result;
            });
        };
        $rootScope.$on('jhipsterApp:pluginUpdate', function(event, result) {
            $scope.plugin = result;
        });
    });
