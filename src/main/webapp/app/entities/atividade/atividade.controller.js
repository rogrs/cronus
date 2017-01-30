(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('AtividadeController', AtividadeController);

    AtividadeController.$inject = ['$scope', '$state', 'Atividade', 'AtividadeSearch'];

    function AtividadeController ($scope, $state, Atividade, AtividadeSearch) {
        var vm = this;

        vm.atividades = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Atividade.query(function(result) {
                vm.atividades = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            AtividadeSearch.query({query: vm.searchQuery}, function(result) {
                vm.atividades = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
