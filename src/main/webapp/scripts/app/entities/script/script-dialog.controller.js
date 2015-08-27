'use strict';

angular.module('jhipsterApp').controller('ScriptDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Script', 'Plugin',
        function($scope, $stateParams, $modalInstance, entity, Script, Plugin) {

        $scope.script = entity;
        $scope.plugins = Plugin.query();
        $scope.load = function(id) {
            Script.get({id : id}, function(result) {
                $scope.script = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:scriptUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.script.id != null) {
                Script.update($scope.script, onSaveFinished);
            } else {
                Script.save($scope.script, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
