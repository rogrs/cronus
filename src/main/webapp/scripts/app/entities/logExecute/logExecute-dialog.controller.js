'use strict';

angular.module('jhipsterApp').controller('LogExecuteDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'LogExecute',
        function($scope, $stateParams, $modalInstance, entity, LogExecute) {

        $scope.logExecute = entity;
        $scope.load = function(id) {
            LogExecute.get({id : id}, function(result) {
                $scope.logExecute = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:logExecuteUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.logExecute.id != null) {
                LogExecute.update($scope.logExecute, onSaveFinished);
            } else {
                LogExecute.save($scope.logExecute, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
