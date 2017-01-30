(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ExecutarPlanoDeleteController',ExecutarPlanoDeleteController);

    ExecutarPlanoDeleteController.$inject = ['$uibModalInstance', 'entity', 'ExecutarPlano'];

    function ExecutarPlanoDeleteController($uibModalInstance, entity, ExecutarPlano) {
        var vm = this;

        vm.executarPlano = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ExecutarPlano.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
