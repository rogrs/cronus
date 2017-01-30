(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('AtividadeDeleteController',AtividadeDeleteController);

    AtividadeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Atividade'];

    function AtividadeDeleteController($uibModalInstance, entity, Atividade) {
        var vm = this;

        vm.atividade = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Atividade.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
