(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PluginController', PluginController);

    PluginController.$inject = ['$scope', '$state', 'Plugin', 'PluginSearch'];

    function PluginController ($scope, $state, Plugin, PluginSearch) {
        var vm = this;

        vm.plugins = [];
        vm.clear = clear;
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Plugin.query(function(result) {
                vm.plugins = result;
                vm.searchQuery = null;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PluginSearch.query({query: vm.searchQuery}, function(result) {
                vm.plugins = result;
                vm.currentSearch = vm.searchQuery;
            });
        }

        function clear() {
            vm.searchQuery = null;
            loadAll();
        }    }
})();
