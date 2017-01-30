'use strict';

describe('Controller Tests', function() {

    describe('Atividade Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAtividade, MockPlano, MockScript;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAtividade = jasmine.createSpy('MockAtividade');
            MockPlano = jasmine.createSpy('MockPlano');
            MockScript = jasmine.createSpy('MockScript');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Atividade': MockAtividade,
                'Plano': MockPlano,
                'Script': MockScript
            };
            createController = function() {
                $injector.get('$controller')("AtividadeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autobotApp:atividadeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
