'use strict';

angular.module('jhipsterApp').controller('PlanoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Plano', 'Projeto',
        function($scope, $stateParams, $modalInstance, entity, Plano, Projeto) {

        $scope.plano = entity;
        $scope.projetos = Projeto.query();
        $scope.load = function(id) {
            Plano.get({id : id}, function(result) {
                $scope.plano = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:planoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.plano.id != null) {
                Plano.update($scope.plano, onSaveFinished);
            } else {
                Plano.save($scope.plano, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
