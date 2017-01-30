(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('PluginDetailController', PluginDetailController);

    PluginDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Plugin', 'Script'];

    function PluginDetailController($scope, $rootScope, $stateParams, previousState, entity, Plugin, Script) {
        var vm = this;

        vm.plugin = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:pluginUpdate', function(event, result) {
            vm.plugin = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
