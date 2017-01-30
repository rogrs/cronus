(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ScriptController', ScriptController);

    ScriptController.$inject = ['$scope', '$state', 'Script', 'ScriptSearch'];

    function ScriptController ($scope, $state, Script, ScriptSearch) {
        var vm = this;

        vm.scripts = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Script.query(function(result) {
                vm.scripts = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ScriptSearch.query({query: vm.searchQuery}, function(result) {
                vm.scripts = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
