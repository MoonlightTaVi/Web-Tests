# Example of Web (UI, API) tests, using [Report Portal](https://demo.reportportal.io) Demo as a source

## UI Test:

- Add widget to the default dashboard

Using:
- Selenium Web Driver (Chrome, Firefox, Edge)

> **Keep in mind**  _The created widgets aren't deleted automatically_  This may show false positive results (because Assertions check if widgets exist, not if they were created)

## API Test:

- POST new dashboard to the Default Personal project
    - Positive (status 201)
    - Negative (status 400)
- DELETE created dashboard (on success) (status 201)

Using:

- Rest Assured

## How to test project:

- `git clone` to some directory
- `Open Maven project`
- `Update Maven project`
- Create `report-portal.properties` file inside `src/test/resources/` (create this directory first if doesn't exist), put the following text into the file:

```
BASE_URL=https://demo.reportportal.io
USER=default
PASSWORD=1q2w3e
API_BASE_URI=https://demo.reportportal.io/api/v1/default_personal/dashboard/
API_KEY=your_api_key
```

_Username and password are publicly available from the Report Portal documentation_

_Generate API key [here](https://demo.reportportal.io/ui/#userProfile/apiKeys)_  (Profile → API Keys → Generate API Key)

- Run both tests from the IDE
- `allure-results` directory must appear in the root
- In PowerShell run `allure` commands:

Generate report:

`allure generate allure-results --clean -o allure-report`

Open report as a web site:

`allure open allure-report`

_You must have Allure Report ([Windows](https://allurereport.org/docs/install-for-windows/)) installed_

