'use strict';

angular.module('jhipsterApp').controller('AtividadeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Atividade', 'Plano', 'Script',
        function($scope, $stateParams, $modalInstance, entity, Atividade, Plano, Script) {

        $scope.atividade = entity;
        $scope.planos = Plano.query();
        $scope.scripts = Script.query();
        $scope.load = function(id) {
            Atividade.get({id : id}, function(result) {
                $scope.atividade = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('jhipsterApp:atividadeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.atividade.id != null) {
                Atividade.update($scope.atividade, onSaveFinished);
            } else {
                Atividade.save($scope.atividade, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
