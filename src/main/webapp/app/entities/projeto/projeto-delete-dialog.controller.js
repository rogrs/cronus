(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ProjetoDeleteController',ProjetoDeleteController);

    ProjetoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Projeto'];

    function ProjetoDeleteController($uibModalInstance, entity, Projeto) {
        var vm = this;

        vm.projeto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Projeto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
