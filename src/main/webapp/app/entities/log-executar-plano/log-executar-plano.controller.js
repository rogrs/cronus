(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('LogExecutarPlanoController', LogExecutarPlanoController);

    LogExecutarPlanoController.$inject = ['$scope', '$state', 'LogExecutarPlano', 'LogExecutarPlanoSearch'];

    function LogExecutarPlanoController ($scope, $state, LogExecutarPlano, LogExecutarPlanoSearch) {
        var vm = this;

        vm.logExecutarPlanos = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            LogExecutarPlano.query(function(result) {
                vm.logExecutarPlanos = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            LogExecutarPlanoSearch.query({query: vm.searchQuery}, function(result) {
                vm.logExecutarPlanos = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
