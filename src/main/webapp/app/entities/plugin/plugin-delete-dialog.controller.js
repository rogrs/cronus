(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PluginDeleteController',PluginDeleteController);

    PluginDeleteController.$inject = ['$uibModalInstance', 'entity', 'Plugin'];

    function PluginDeleteController($uibModalInstance, entity, Plugin) {
        var vm = this;

        vm.plugin = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Plugin.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
