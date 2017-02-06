(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('LogExecutarPlanoDialogController', LogExecutarPlanoDialogController);

    LogExecutarPlanoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LogExecutarPlano', 'ExecutarPlano'];

    function LogExecutarPlanoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LogExecutarPlano, ExecutarPlano) {
        var vm = this;

        vm.logExecutarPlano = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.executarplanos = ExecutarPlano.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.logExecutarPlano.id !== null) {
                LogExecutarPlano.update(vm.logExecutarPlano, onSaveSuccess, onSaveError);
            } else {
                LogExecutarPlano.save(vm.logExecutarPlano, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('autobotApp:logExecutarPlanoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.criado = false;
        vm.datePickerOpenStatus.finalizado = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
