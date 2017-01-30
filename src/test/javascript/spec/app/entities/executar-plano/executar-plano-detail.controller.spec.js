'use strict';

describe('Controller Tests', function() {

    describe('ExecutarPlano Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockExecutarPlano, MockScript, MockPlano;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockExecutarPlano = jasmine.createSpy('MockExecutarPlano');
            MockScript = jasmine.createSpy('MockScript');
            MockPlano = jasmine.createSpy('MockPlano');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ExecutarPlano': MockExecutarPlano,
                'Script': MockScript,
                'Plano': MockPlano
            };
            createController = function() {
                $injector.get('$controller')("ExecutarPlanoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'autobotApp:executarPlanoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
