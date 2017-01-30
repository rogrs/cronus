(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PlanoController', PlanoController);

    PlanoController.$inject = ['$scope', '$state', 'Plano', 'PlanoSearch'];

    function PlanoController ($scope, $state, Plano, PlanoSearch) {
        var vm = this;

        vm.planos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Plano.query(function(result) {
                vm.planos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PlanoSearch.query({query: vm.searchQuery}, function(result) {
                vm.planos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
