(function() {
    'use strict';

    angular
        .module('autobotApp')
        .controller('AtividadeDetailController', AtividadeDetailController);

    AtividadeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Atividade', 'Plano', 'Script'];

    function AtividadeDetailController($scope, $rootScope, $stateParams, previousState, entity, Atividade, Plano, Script) {
        var vm = this;

        vm.atividade = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('autobotApp:atividadeUpdate', function(event, result) {
            vm.atividade = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
