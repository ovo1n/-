// main UI JS: smooth scroll, adopt/login prompts and favorite toggle
document.addEventListener('DOMContentLoaded', function() {
  // Smooth scroll for anchors
  document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
      const href = this.getAttribute('href');
      if (href && href.startsWith('#')) {
        e.preventDefault();
        const target = document.querySelector(href);
        if (target) target.scrollIntoView({ behavior: 'smooth' });
      }
    });
  });

  // Adopt buttons: redirect to login if not authenticated (placeholder behavior)
  document.querySelectorAll('.btn-adopt').forEach(btn => {
    btn.addEventListener('click', function(e) {
      // If server-side rendered with user info, this can be conditional
      alert('请先登录以提交领养申请');
      window.location.href = '/login';
    });
  });

  // Favorite toggle
  document.querySelectorAll('.btn-favorite').forEach(btn => {
    btn.addEventListener('click', function() {
      this.classList.toggle('active');
      if (this.classList.contains('active')) {
        this.style.background = 'linear-gradient(90deg,var(--primary-color-2),var(--primary-color))';
        this.style.color = 'white';
      } else {
        this.style.background = '#f3f4f6';
        this.style.color = 'var(--primary-color)';
      }
    });
  });
});
