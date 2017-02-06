(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ScriptDialogController', ScriptDialogController);

    ScriptDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Script', 'Plano', 'Plugin'];

    function ScriptDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Script, Plano, Plugin) {
        var vm = this;

        vm.script = entity;
        vm.clear = clear;
        vm.save = save;
        vm.planos = Plano.query();
        vm.plugins = Plugin.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.script.id !== null) {
                Script.update(vm.script, onSaveSuccess, onSaveError);
            } else {
                Script.save(vm.script, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('autobotApp:scriptUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
