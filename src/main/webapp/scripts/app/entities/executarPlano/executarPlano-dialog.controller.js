'use strict';

angular.module('jhipsterApp').controller('ExecutarPlanoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'ExecutarPlano', 'Atividade',
        function($scope, $stateParams, $modalInstance, entity, ExecutarPlano, Atividade) {

        $scope.executarPlano = entity;
        $scope.atividades = Atividade.query();
        $scope.load = function(id) {
            ExecutarPlano.get({id : id}, function(result) {
                $scope.executarPlano = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:executarPlanoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.executarPlano.id != null) {
                ExecutarPlano.update($scope.executarPlano, onSaveFinished);
            } else {
                ExecutarPlano.save($scope.executarPlano, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
