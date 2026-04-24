<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1"/>
                <title>Drone Sales - manager dashboard</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous"/>
            </head>
            <body class="bg-light">
                <div class="container mt-5">
                    <div class="card shadow">
                        <div class="card-header bg-primary text-white">
                            <h3 class="m-0 text-center">Drone Sales Report</h3>
                        </div>
                        <div class="card-body p-0">
                            <table class="table table-hover table-striped mb-0">
                                <thead class="table-dark">
                                    <tr>
                                        <th style="width: 5%">ID</th>
                                        <th style="width: 25%">Account Number</th>
                                        <th style="width: 20%">Manager</th>
                                        <th style="width: 50%">Sold Drones</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <xsl:for-each select="managers/manager">
                                        <tr class="align-middle">
                                            <td class="fw-bold"><xsl:value-of select="id"/></td>
                                            <td><span class="font-monospace"><xsl:value-of select="accountNumber"/></span></td>
                                            <td><span class="badge bg-info text-dark"><xsl:value-of select="username"/></span></td>
                                            <td>
                                                <xsl:choose>
                                                    <xsl:when test="soldDrones/drone">
                                                        <table class="table table-sm table-bordered m-0 bg-light">
                                                            <thead class="small text-muted">
                                                                <tr>
                                                                    <th>Drone Name</th>
                                                                    <th>Price</th>
                                                                    <th>Battery</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <xsl:for-each select="soldDrones/drone">
                                                                    <tr>
                                                                        <td><xsl:value-of select="droneName"/></td>
                                                                        <td class="text-success fw-bold"><xsl:value-of select="price"/>€</td>
                                                                        <td><xsl:value-of select="batteryCapacity"/> mAh</td>
                                                                    </tr>
                                                                </xsl:for-each>
                                                            </tbody>
                                                        </table>
                                                    </xsl:when>
                                                    <xsl:otherwise>
                                                        <em class="text-muted small">No drones sold yet.</em>
                                                    </xsl:otherwise>
                                                </xsl:choose>
                                            </td>
                                        </tr>
                                    </xsl:for-each>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="card shadow mt-5">
                        <div class="card-header bg-primary text-white">
                            <h3 class="m-0 text-center">Drone Sales Report</h3>
                        </div>
                        <div class="card-body p-0">
                            <table class="table table-hover table-striped mb-0">
                                <thead class="table-success">
                                    <tr>
                                        <th style="width: 50%">Totally sold drones - <xsl:value-of select="count(//drone)"/></th>
                                        <th style="width: 50%">Totally earned - <xsl:value-of select="sum(//soldDrones/drone/price)"/>$</th>
                                    </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </body>
            <footer>
                <div class="container mt-4">
                    <h5 class="fs-1 text-primary text-end">Table was made by Arnoldas Strukčinskas</h5>
                </div>
            </footer>
        </html>
    </xsl:template>
</xsl:stylesheet>