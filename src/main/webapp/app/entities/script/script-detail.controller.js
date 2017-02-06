(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('ScriptDetailController', ScriptDetailController);

    ScriptDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Script', 'Plano', 'Plugin'];

    function ScriptDetailController($scope, $rootScope, $stateParams, previousState, entity, Script, Plano, Plugin) {
        var vm = this;

        vm.script = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:scriptUpdate', function(event, result) {
            vm.script = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
