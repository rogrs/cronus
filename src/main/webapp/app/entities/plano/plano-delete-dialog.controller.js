(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PlanoDeleteController',PlanoDeleteController);

    PlanoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Plano'];

    function PlanoDeleteController($uibModalInstance, entity, Plano) {
        var vm = this;

        vm.plano = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Plano.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
