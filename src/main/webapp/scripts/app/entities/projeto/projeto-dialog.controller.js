'use strict';

angular.module('jhipsterApp').controller('ProjetoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Projeto',
        function($scope, $stateParams, $modalInstance, entity, Projeto) {

        $scope.projeto = entity;
        $scope.load = function(id) {
            Projeto.get({id : id}, function(result) {
                $scope.projeto = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:projetoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.projeto.id != null) {
                Projeto.update($scope.projeto, onSaveFinished);
            } else {
                Projeto.save($scope.projeto, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
