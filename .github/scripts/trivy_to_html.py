import json
import os
from pathlib import Path
from datetime import datetime

# Get repository name and current UTC scan time
repo_name = os.getenv("GITHUB_REPOSITORY", "Unknown repository")
scan_date = datetime.utcnow().strftime("%Y-%m-%d %H:%M UTC")

# Safely load the Trivy report JSON
if not os.path.exists("trivy-report.json") or os.path.getsize("trivy-report.json") == 0:
    data = {}
else:
    with open("trivy-report.json", "r") as f:
        try:
            data = json.load(f)
        except json.JSONDecodeError:
            data = {}

# Start building the HTML report
html = [
    '<html><body>',
    '<h2>Trivy Scan Report</h2>',
    f'<p><strong>Repository:</strong> {repo_name}<br>',
    f'<strong>Scan date:</strong> {scan_date}</p>'
]

# Check for results in the report
results = data.get('Results')
if not results:
    html.append("<p><strong>No vulnerabilities found.</strong></p>")
else:
    found = False
    for result in results:
        vulnerabilities = result.get('Vulnerabilities')
        if not vulnerabilities:
            continue

        found = True
        html.append(f"<h3>Target: {result.get('Target')}</h3>")
        html.append("<table border='1' cellpadding='5' cellspacing='0'>")
        html.append("<tr><th>Vulnerability ID</th><th>Pkg Name</th><th>Installed Version</th><th>Fixed Version</th><th>Severity</th><th>Title</th></tr>")

        for vuln in vulnerabilities:
            html.append("<tr>")
            html.append(f"<td>{vuln.get('VulnerabilityID')}</td>")
            html.append(f"<td>{vuln.get('PkgName')}</td>")
            html.append(f"<td>{vuln.get('InstalledVersion')}</td>")
            html.append(f"<td>{vuln.get('FixedVersion', 'N/A')}</td>")
            html.append(f"<td>{vuln.get('Severity')}</td>")
            html.append(f"<td>{vuln.get('Title', '')}</td>")
            html.append("</tr>")

        html.append("</table><br>")

    # If no vulnerabilities found in any result block
    if not found:
        html.append("<p><strong>No vulnerabilities found.</strong></p>")

# Finalize HTML document
html.append("</body></html>")

# Write the result to email-body.html
with open('email-body.html', 'w') as f:
    f.write('\n'.join(html))

# Create a flag file if vulnerabilities were found (used in GitHub Actions)
if found:
    Path("vulns-found.flag").touch()
