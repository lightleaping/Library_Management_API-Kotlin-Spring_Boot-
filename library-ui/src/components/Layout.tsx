import { Link, Outlet, useLocation } from "react-router-dom";

export default function Layout() {
    const { pathname } = useLocation();
    return (
        <div className="min-h-screen bg-gray-50">
            <header className="border-b bg-white">
                <div className="mx-auto max-w-5xl px-4 py-3 flex items-center justify-between">
                    <Link to="/books" className="text-xl font-semibold">📚 Library</Link>
                    <nav className="text-sm">
                        <Link
                            to="/books"
                            className={`px-3 py-1 rounded ${pathname.startsWith("/books") ? "bg-gray-900 text-white" : "hover:bg-gray-200"}`}
                        >
                            Books
                        </Link>
                    </nav>
                </div>
            </header>

            <main className="mx-auto max-w-5xl px-4 py-6">
                <Outlet />
            </main>
        </div>
    );
}