(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PluginDialogController', PluginDialogController);

    PluginDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Plugin', 'Script'];

    function PluginDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Plugin, Script) {
        var vm = this;

        vm.plugin = entity;
        vm.clear = clear;
        vm.save = save;
        vm.scripts = Script.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.plugin.id !== null) {
                Plugin.update(vm.plugin, onSaveSuccess, onSaveError);
            } else {
                Plugin.save(vm.plugin, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('autobotApp:pluginUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
