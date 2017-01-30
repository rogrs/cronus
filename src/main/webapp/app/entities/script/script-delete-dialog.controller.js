(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ScriptDeleteController',ScriptDeleteController);

    ScriptDeleteController.$inject = ['$uibModalInstance', 'entity', 'Script'];

    function ScriptDeleteController($uibModalInstance, entity, Script) {
        var vm = this;

        vm.script = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Script.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
