(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ExecutarPlanoController', ExecutarPlanoController);

    ExecutarPlanoController.$inject = ['$scope', '$state', 'ExecutarPlano', 'ExecutarPlanoSearch'];

    function ExecutarPlanoController ($scope, $state, ExecutarPlano, ExecutarPlanoSearch) {
        var vm = this;

        vm.executarPlanos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            ExecutarPlano.query(function(result) {
                vm.executarPlanos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ExecutarPlanoSearch.query({query: vm.searchQuery}, function(result) {
                vm.executarPlanos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
