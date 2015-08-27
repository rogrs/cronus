'use strict';

angular.module('jhipsterApp').controller('PluginDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Plugin',
        function($scope, $stateParams, $modalInstance, entity, Plugin) {

        $scope.plugin = entity;
        $scope.load = function(id) {
            Plugin.get({id : id}, function(result) {
                $scope.plugin = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:pluginUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.plugin.id != null) {
                Plugin.update($scope.plugin, onSaveFinished);
            } else {
                Plugin.save($scope.plugin, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
