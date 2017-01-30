(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ProjetoController', ProjetoController);

    ProjetoController.$inject = ['$scope', '$state', 'Projeto', 'ProjetoSearch'];

    function ProjetoController ($scope, $state, Projeto, ProjetoSearch) {
        var vm = this;

        vm.projetos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Projeto.query(function(result) {
                vm.projetos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ProjetoSearch.query({query: vm.searchQuery}, function(result) {
                vm.projetos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
