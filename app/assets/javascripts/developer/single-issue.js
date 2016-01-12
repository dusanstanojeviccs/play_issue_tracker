app.controller('SingleIssueController', function($scope, $http) {
    $scope.status = Preset.status;

    $scope.changeIssueStatus = function() {
        $http.get(Routes.changeIssueStatus).success(function(data, status) {
            if (data==="Ok") {
				$scope.status = "Waiting Approval";	
            } else {
				$scope.error = true;
            }
        }).error(function(data, status) {
			$scope.error = true;
        });
    };
});