(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('LogExecutarPlanoDeleteController',LogExecutarPlanoDeleteController);

    LogExecutarPlanoDeleteController.$inject = ['$uibModalInstance', 'entity', 'LogExecutarPlano'];

    function LogExecutarPlanoDeleteController($uibModalInstance, entity, LogExecutarPlano) {
        var vm = this;

        vm.logExecutarPlano = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LogExecutarPlano.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
