(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ExecutarPlanoDialogController', ExecutarPlanoDialogController);

    ExecutarPlanoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ExecutarPlano', 'LogExecutarPlano', 'Plano'];

    function ExecutarPlanoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ExecutarPlano, LogExecutarPlano, Plano) {
        var vm = this;

        vm.executarPlano = entity;
        vm.clear = clear;
        vm.save = save;
        vm.logexecutarplanos = LogExecutarPlano.query();
        vm.planos = Plano.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.executarPlano.id !== null) {
                ExecutarPlano.update(vm.executarPlano, onSaveSuccess, onSaveError);
            } else {
                ExecutarPlano.save(vm.executarPlano, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('autobotApp:executarPlanoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
