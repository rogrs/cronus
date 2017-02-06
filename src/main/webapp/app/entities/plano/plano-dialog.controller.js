(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PlanoDialogController', PlanoDialogController);

    PlanoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Plano', 'Atividade', 'ExecutarPlano', 'Projeto', 'Script'];

    function PlanoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Plano, Atividade, ExecutarPlano, Projeto, Script) {
        var vm = this;

        vm.plano = entity;
        vm.clear = clear;
        vm.save = save;
        vm.atividades = Atividade.query();
        vm.executarplanos = ExecutarPlano.query();
        vm.projetos = Projeto.query();
        vm.scripts = Script.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.plano.id !== null) {
                Plano.update(vm.plano, onSaveSuccess, onSaveError);
            } else {
                Plano.save(vm.plano, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('autobotApp:planoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
