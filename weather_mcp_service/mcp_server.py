import requests
from mcp.server.fastmcp import FastMCP

mcp = FastMCP("ATM-Finance-Assistant")

@mcp.tool()
def doviz_cevirici(miktar: float, hedef_kur: str) -> str:
    """ATM kullanicisi icin USD miktarini istenen kura cevirir."""
    url = "https://api.exchangerate-api.com/v4/latest/USD"
    resp = requests.get(url).json()
    kur = resp['rates'].get(hedef_kur.upper())
    if kur:
        sonuc = miktar * kur
        return f"{miktar} USD = {sonuc:.2f} {hedef_kur}"
    return "Kur bilgisi alinamadi."

if __name__ == "__main__":
    mcp.run()