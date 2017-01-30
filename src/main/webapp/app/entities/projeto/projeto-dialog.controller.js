(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ProjetoDialogController', ProjetoDialogController);

    ProjetoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Projeto', 'Plano'];

    function ProjetoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Projeto, Plano) {
        var vm = this;

        vm.projeto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.planos = Plano.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.projeto.id !== null) {
                Projeto.update(vm.projeto, onSaveSuccess, onSaveError);
            } else {
                Projeto.save(vm.projeto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('autobotApp:projetoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
